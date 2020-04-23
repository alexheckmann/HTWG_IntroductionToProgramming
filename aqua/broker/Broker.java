package aqua.broker;

import aqua.common.Direction;
import aqua.common.FishModel;
import aqua.common.Properties;
import aqua.common.msgtypes.DeregisterRequest;
import aqua.common.msgtypes.HandoffRequest;
import aqua.common.msgtypes.RegisterRequest;
import aqua.common.msgtypes.RegisterResponse;
import messaging.Endpoint;
import messaging.Message;

import javax.swing.*;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Broker {

    private final ClientCollection<InetSocketAddress> availableClients;
    private final Endpoint endpoint;
    private volatile boolean stopRequested;
    private static final int THREAD_POOL_SIZE = (int) (Runtime.getRuntime().availableProcessors() / 0.5);

    public Broker() {

        availableClients = new ClientCollection<>();
        endpoint = new Endpoint(Properties.PORT);
        stopRequested = false;

    }

    public synchronized boolean getStopRequested() {
        return stopRequested;
    }

    public synchronized void setStopRequested(boolean stopRequested) {
        this.stopRequested = stopRequested;
    }

    public void broker() {

        Thread terminateServerThread = new Thread(() -> {

            JOptionPane.showMessageDialog(null, "Press OK button to terminate server.");
            setStopRequested(true);

        });

        terminateServerThread.start();

        // using the executor framework to manage a thread pool of fixed size
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);


        while (!stopRequested) {

            Message message = endpoint.blockingReceive();
            executorService.execute(new BrokerTask(message));

        }

        executorService.shutdown();
    }

    private class BrokerTask implements Runnable {

        private final Message message;

        private BrokerTask(Message incomingMessage) {
            message = incomingMessage;
        }

        @Override
        public void run() {

            // making use of a read write lock to enable multithreaded execution
            ReadWriteLock lock = new ReentrantReadWriteLock();
            lock.writeLock().lock();

            Serializable payload = message.getPayload();
            InetSocketAddress sender = message.getSender();

            lock.writeLock().unlock();

            if (payload instanceof RegisterRequest) {

                synchronized (availableClients) {
                    availableClients.add("tank" + (availableClients.size() + 1), message.getSender());
                    endpoint.send(sender, new RegisterResponse("tank" + availableClients.size()));
                }

            } else if (payload instanceof DeregisterRequest) {

                synchronized (availableClients) {
                    availableClients.remove(availableClients.indexOf(sender));
                }

            } else if (payload instanceof HandoffRequest) {

                // making use of a read write lock to enable multithreaded execution
                lock.writeLock().lock();

                FishModel fish = ((HandoffRequest) payload).getFish();
                Direction exitDirection = fish.getDirection();

                switch (exitDirection) {
                    case LEFT:
                        endpoint.send(availableClients.getLeftNeighorOf(availableClients.indexOf(sender)), payload);
                        break;
                    case RIGHT:
                        endpoint.send(availableClients.getRightNeighorOf(availableClients.indexOf(sender)), payload);
                        break;
                }

                lock.writeLock().unlock();

            } else if (payload instanceof PoisonPill) {

                System.exit(0);

            }

        }
    }

    public static void main(String[] args) {

        Broker broker = new Broker();
        broker.broker();


    }

}

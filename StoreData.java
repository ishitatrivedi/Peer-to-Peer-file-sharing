package CNT5106Project;

import messages.Bitfield;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StoreData // Stores data for each peer
{
    // Common config
    public static int numPreferredNeighbors = -1;
    public static int unchokingInterval = -1;
    public static int optimisticUCInterval = -1;
    public static String fileName = "";
    public static int fileSize = 0;
    public static int pieceSize = 0;

    // Peer config
    public static int peerId = -1;
    public static String hostName = "";
    public static int listeningPort = -1;
    public static int numOfPieces = 0;
    public static ArrayList<Integer> peerList = null;

    private static final Object PEERS_PAYLOAD = new Object();
    private volatile static HashMap<Integer, HashSet<Integer>> peersPayload = new HashMap<Integer, HashSet<Integer>>();

    private static final Object DOWNLOAD_MAP = new Object();
    private static ConcurrentHashMap<Integer, Integer> downloadMap = new ConcurrentHashMap<>();

    private static final Object START_DOWNLOAD_TIME = new Object();
    private static ConcurrentHashMap<Integer, Long> startTimeList = new ConcurrentHashMap<>();

    private static final Object PREFERRED_PEERS = new Object();
    private volatile static HashSet<Integer> preferredPeerList = new HashSet<>();

    private volatile static ArrayList<Integer> interestedPeers = new ArrayList<>();
    public static final Object INTERESTED_PEERS = new Object();

    private volatile static int optNeighbor = -1;
    private static final Object OPT_NEIGHBOR = new Object();

    private volatile static StringBuilder pieceInfo = new StringBuilder();
    private static final Object PIECE_INFO = new Object();

    public static boolean hasFullFile = false;
    public static boolean getHasFullFile()
    {
        return hasFullFile;
    }
    public static void setHasFullFile(boolean val)
    {
        hasFullFile = val;
    }

    // Initialize Peers piece indices
    public static void initializePeersPayload()
    {
        synchronized (PEERS_PAYLOAD)
        {
            for(int peerId : peerList)
            {
                if(!peersPayload.containsKey(peerId))
                {
                    peersPayload.put(peerId, new HashSet<>());
                }
            }
        }
    }

    // Initialize download map
    public static void initializeDownloadMap()
    {
        synchronized (DOWNLOAD_MAP)
        {
            for (int peerId : peerList)
            {
                downloadMap.put(peerId, 0);
            }
        }
    }

    // Initialize download start time
    public static void initializeDownloadTime()
    {
        synchronized (START_DOWNLOAD_TIME)
        {
            for (int peerId : peerList)
            {
                startTimeList.put(peerId, (long) 0);
            }
        }
    }

    public static boolean isPeerInteresting(Bitfield bf)
    {
        synchronized (StoreData.PIECE_INFO)
        {
            for(int i = 0; i < bf.getPayload().length(); i++)
            {
                if(pieceInfo.charAt(i) == '0' && bf.getPayload().charAt(i) == '1')
                {
                    return true;
                }
            }
        }
        return false;
    }

    // Get Preferred Neigbhors
    public static HashSet<Integer> getPreferredNeighbors()
    {
        HashMap<Integer, Long> startMap = null;
        HashMap<Integer, Integer> downloadMap = null;
        long current = System.currentTimeMillis(); // current time
        synchronized (DOWNLOAD_MAP)
        {
            startMap = new HashMap<>(startTimeList);
            downloadMap = new HashMap<>(downloadMap);

            for(Map.Entry<Integer, Integer> val : downloadMap.entrySet())
            {
                downloadMap.put(val.getKey(), 0);
                startTimeList.put(val.getKey(), (long) 0);
            }
        }
        HashMap<Integer, Double> targetRates = new HashMap<>(); // Rates of interested Peers

        synchronized (INTERESTED_PEERS)
        {
            for (int interestedPeer : interestedPeers)
            {
                double rate = (double) downloadMap.get(interestedPeer) / (current - startMap.get(interestedPeer));
                targetRates.put(interestedPeer, rate);
            }
        }

        LinkedHashMap<Integer, Double> ratesSorted = (LinkedHashMap<Integer, Double>) sort(targetRates);

        int chosen = 0;
        HashSet<Integer> prefNeighbors = new HashSet();

        for (Map.Entry<Integer, Double> val : ratesSorted.entrySet())
        {
            prefNeighbors.add(val.getKey());
            chosen++;
            if (chosen == numPreferredNeighbors) break;
        }

        synchronized (PREFERRED_PEERS)
        {
            preferredPeerList = prefNeighbors;
        }
        return prefNeighbors;
    }


    // Get Random Neighbors
    public static HashSet<Integer> getRandomNeighbors()
    {
        ArrayList<Integer> interestedNs = null;

        synchronized (INTERESTED_PEERS)
        {
            interestedNs = new ArrayList<>(interestedPeers);
        }
        // Randomize arrayList
        Collections.shuffle(interestedNs);
        HashSet<Integer> randomList = new HashSet<>();
        int iterate = interestedNs.size() < numPreferredNeighbors? interestedNs.size() : numPreferredNeighbors;

        for (int i = 0; i < iterate; i++)
        {
            randomList.add(interestedNs.get(i));
        }

        synchronized (PREFERRED_PEERS)
        {
            preferredPeerList = randomList;
        }
        return randomList;
    }

    // Get Optimistic Neighbors
    public static int getOptimisticNeighbors()
    {
        HashSet<Integer> chokedpeerList = null;
        synchronized (INTERESTED_PEERS)
        {
            chokedpeerList = new HashSet<>(interestedPeers);
        }
        synchronized (PREFERRED_PEERS)
        {
            chokedpeerList.removeAll(preferredPeerList);
        }
        synchronized (OPT_NEIGHBOR)
        {
            chokedpeerList.remove(optNeighbor);
        }

        ArrayList<Integer> potential = new ArrayList<>(chokedpeerList);
        Collections.shuffle(potential);
        if (potential.size() > 0)
        {
            optNeighbor = potential.get(0);
            return optNeighbor;
        }
        return -1;
    }

    // Get Piece Information
    public static String getPieceInfo()
    {
        synchronized (PIECE_INFO)
        {
            return pieceInfo.toString();
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sort(Map<K, V> map)
    {
        List<Map.Entry<K, V>> listToSort = new ArrayList<>(map.entrySet());
        listToSort.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();

        for (Map.Entry<K, V> entry : listToSort)
        {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}

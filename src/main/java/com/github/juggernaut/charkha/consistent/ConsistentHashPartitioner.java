package com.github.juggernaut.charkha.consistent;

import com.github.juggernaut.charkha.Node;
import com.github.juggernaut.charkha.Partitioner;
import com.github.juggernaut.charkha.hashing.HashFunction;
import com.github.juggernaut.charkha.hashing.Murmur3HashFunction;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Code courtesy Tom E White - http://www.tom-e-white.com/2007/11/consistent-hashing.html
 */
public class ConsistentHashPartitioner implements Partitioner {

    private final HashFunction hashFunction;
    private final SortedMap<Integer, Node> circle = new TreeMap<>();

    public ConsistentHashPartitioner() {
        this(new Murmur3HashFunction());
    }

    public ConsistentHashPartitioner(final HashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    public ConsistentHashPartitioner(final HashFunction hashFunction, final Collection<Node> nodes) {
        this.hashFunction = hashFunction;
        nodes.forEach(this::addNode);
    }

    @Override
    public void addNode(final Node n) {
        circle.put(hashFunction.hash32(n.getKey()), n);
    }

    @Override
    public void removeNode(final Node n) {
        circle.remove(hashFunction.hash32(n.getKey()));
    }

    @Override
    public Node getNodeForKey(final byte[] key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = hashFunction.hash32(key);
        if (!circle.containsKey(hash)) {
            final SortedMap<Integer, Node> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }
}

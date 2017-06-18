package com.github.juggernaut.charkha;

/**
 * @author ameya
 */
public interface Partitioner {

    void addNode(Node n);

    void removeNode(Node n);

    Node getNodeForKey(byte[] key);

}

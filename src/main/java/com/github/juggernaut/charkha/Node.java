package com.github.juggernaut.charkha;

/**
 * An interface that represents a node on the consistent hash ring.
 *
 * @author ameya
 */
public interface Node {

    byte[] getKey();
}

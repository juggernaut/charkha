package com.github.juggernaut.charkha.hashing;

/**
 * @author ameya
 */
public class Murmur3HashFunction implements HashFunction {

    @Override
    public int hash32(byte[] key) {
        return Murmur3.hash32(key);
    }
}

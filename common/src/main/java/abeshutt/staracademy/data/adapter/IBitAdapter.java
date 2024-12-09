package abeshutt.staracademy.data.adapter;

import abeshutt.staracademy.data.bit.BitBuffer;

import java.util.Optional;

public interface IBitAdapter<T, C> {

    void writeBits(T value, BitBuffer buffer, C context);

    Optional<T> readBits(BitBuffer buffer, C context);

}

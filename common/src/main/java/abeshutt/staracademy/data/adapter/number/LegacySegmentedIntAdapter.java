package abeshutt.staracademy.data.adapter.number;

import abeshutt.staracademy.data.bit.BitBuffer;

public class LegacySegmentedIntAdapter extends IntAdapter {

    public static final LegacySegmentedIntAdapter _3 = new LegacySegmentedIntAdapter(3, false);
    public static final LegacySegmentedIntAdapter _7 = new LegacySegmentedIntAdapter(7, false);

    private final int bitSegment;
    private final int byteSegment;

    public LegacySegmentedIntAdapter(int segment, boolean nullable) {
        super(nullable);
        this.bitSegment = segment;

        if(segment < 8) this.byteSegment = 1;
        else if(segment < 16) this.byteSegment = 2;
        else if(segment < 24) this.byteSegment = 3;
        else if(segment < 32) this.byteSegment = 4;
        else this.byteSegment = 5;
    }

    public int getBitSegment() {
        return this.bitSegment;
    }

    public int getByteSegment() {
        return this.byteSegment;
    }

    public LegacySegmentedIntAdapter asNullable() {
        return new LegacySegmentedIntAdapter(this.bitSegment, true);
    }

    @Override
    protected void writeNumberBits(Integer value, BitBuffer buffer) {
        int segment = this.bitSegment;
        int mask = (1 << segment) - 1;

        while(true) {
            long bits = value & mask;
            value >>>= segment;

            if(value == 0) {
                buffer.writeLongBits((1L << segment) | bits, segment + 1);
                break;
            }

            buffer.writeLongBits(bits, segment + 1);
        }
    }

    @Override
    protected Integer readNumberBits(BitBuffer buffer) {
        int segment = this.bitSegment;
        int mask = 1 << segment;
        int value = 0;

        for(int shift = 0; ; shift += segment) {
            long bits = buffer.readLongBits(segment + 1);

            if((bits & mask) != 0) {
                value |= (bits - mask) << shift;
                return value;
            }

            value |= bits << shift;
        }
    }

}

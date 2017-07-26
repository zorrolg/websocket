package net.paiyou.service.protocol;

public class Request {
    private long index;
    private short  cmd;
    private byte[] data;
    public Request(){}

    public Request(long index, int cmd, byte[] data) {
        this.index = index;
        this.cmd = (short)cmd;
        this.data = data;
    }


    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

package net.paiyou.service.protocol;

public class Response extends Request{
	private static long index = 0;
    private short statusCode;

    public Response(int cmd, byte[] data) {
    	super(++index, cmd, data);
    	this.statusCode = 0;
    }
    public Response(long index, int cmd, byte[] data, short statusCode) {
        super(index, cmd, data);
        this.statusCode = statusCode;
    }

    public short getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(short statusCode) {
        this.statusCode = statusCode;
    }
}

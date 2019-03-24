package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private boolean isNewLine = true;
    private int lineNo = 1;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        // filter file numbering 1
        for (int i = off; i < off + len; ++i)
            this.write(str.charAt(i));

    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        // filter file numbering 2
        for (int i = off; i < off + len; ++i)
            this.write(cbuf[i]);

    }

    @Override
    public void write(int c) throws IOException {
        // filter file numbering 3
        if (lineNo == 1 || isNewLine && c != '\n')
            out.write(lineNo++ + "\t");

        isNewLine = c == '\r';
        out.write(c);

        if (!isNewLine && c == '\n')
            out.write(lineNo++ + "\t");
    }

}

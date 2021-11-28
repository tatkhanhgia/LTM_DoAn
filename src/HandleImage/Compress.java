package HandleImage;

import ij.ImagePlus;
import ij.io.FileSaver;

public class Compress {
    public static void main(String[] args) {
        ImagePlus imgPlus = new ImagePlus("src/HandleImage/Before/a.jpg");

        FileSaver file = new FileSaver(imgPlus);
        file.setJpegQuality(10); // 0-100

        file.saveAsJpeg("src/HandleImage/After/e.jpg");
    }
}

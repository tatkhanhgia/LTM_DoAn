package HandleImage;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageConverter;

public class GrayScale {
    public static void main(String[] args) {
        ImagePlus imgPlus = new ImagePlus("src/HandleImage/Before/a.jpg");

        new ImageConverter(imgPlus).convertToGray8();

        FileSaver file = new FileSaver(imgPlus);

        file.saveAsJpeg("src/HandleImage/After/a.jpg");
    }
}

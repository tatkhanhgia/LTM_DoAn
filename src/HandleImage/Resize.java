package HandleImage;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;

public class Resize {
    public static void main(String[] args) {
        ImagePlus resizeImage = new ImagePlus("src/HandleImage/Before/a.jpg");
        ImagePlus cropImage = new ImagePlus("src/HandleImage/Before/a.jpg");

        cropImage.setRoi(200, 200, 700, 500);

        ImageProcessor resizeImageProcessor = resizeImage.getProcessor().resize(700, 500, false);
        ImageProcessor cropImageProcessor = cropImage.getProcessor().crop();

        resizeImage.setProcessor(resizeImageProcessor);
        cropImage.setProcessor(cropImageProcessor);

        FileSaver fileResize = new FileSaver(resizeImage);
        FileSaver fileCrop = new FileSaver(cropImage);

        fileResize.saveAsJpeg("src/HandleImage/After/b.jpg");
        fileCrop.saveAsJpeg("src/HandleImage/After/c.jpg");
    }
}

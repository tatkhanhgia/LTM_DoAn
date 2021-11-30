package HandleImage;

import ij.ImagePlus;
import ij.io.FileSaver;

//5 formats : TIFF, JPEG, GIF, PNG, RAW
public class Format {
    public static void main(String[] args) {
        ImagePlus source = new ImagePlus("src/HandleImage/Before/a.jpg");

        FileSaver file = new FileSaver(source);

        file.saveAsTiff("src/HandleImage/After/d.tif");
        file.saveAsJpeg("src/HandleImage/After/d.jpg");
        file.saveAsGif("src/HandleImage/After/d.gif");
        file.saveAsPng("src/HandleImage/After/d.png");
//        file.saveAsRaw("src/HandleImage/After/d.raw"); not working
    }
}

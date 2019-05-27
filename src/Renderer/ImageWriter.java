package Renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("ALL")
public class ImageWriter
{

    private int _imageWidth;
    private int _imageHeight;
    private int _Ny, _Nx;
    final String PROJECT_PATH = System.getProperty("user.dir");
    private BufferedImage _image;
    private String _imageName;

    // ***************** Constructors ********************** //
    public ImageWriter(String imageName, int width, int height, int Ny, int Nx){

        _Nx = Nx;
        _Ny = Ny;

        _imageWidth = width;
        _imageHeight = height;

        _imageName = imageName;

        _image = new BufferedImage(
                _imageWidth, _imageHeight, BufferedImage.TYPE_INT_RGB);;
    }
    public ImageWriter (ImageWriter imageWriter){
        _Nx = imageWriter._Nx;
        _Ny = imageWriter._Ny;

        _imageWidth = imageWriter.getWidth();
        _imageHeight = imageWriter.getHeight();

        _imageName = imageWriter._imageName;

        _image = new BufferedImage(
                _imageWidth, _imageHeight, BufferedImage.TYPE_INT_RGB);;
    }

    // ***************** Getters/Setters ********************** //
    public int getWidth()  { return _imageWidth;  }
    public int getHeight() { return _imageHeight; }

    public int getNy() { return _Ny; }
    public int getNx() { return _Nx; }

    public void setNy(int _Ny) { this._Ny = _Ny; }
    public void setNx(int _Nx) { this._Nx = _Nx; }

    // ***************** Operations ******************** //
    /************************************************
     * FUNCTION
     *  writeToimage
     * @param
     * @return
     * MEANING
     *  This functions creates the new File for the new image we will need to write
     *************************************************/
    public void writeToimage(){

        File ouFile = new File(PROJECT_PATH + "/" + _imageName + ".jpg");

        try {
            ImageIO.write(_image, "jpg", ouFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /************************************************
     * FUNCTION
     *  subtract
     * @param xIndex index of x
     * @param yIndex index of y
     * @param r red
     * @param g green
     * @param b blue
     * @return
     * MEANING
     *  This functions writes the pixel (x,y) with the color [r,g,b]
     *************************************************/
    public void writePixel(int xIndex, int yIndex, int r, int g, int b){
        int rgb = new Color(r, g, b).getRGB();
        _image.setRGB(xIndex, yIndex, rgb);
    }
    /************************************************
     * FUNCTION
     *  writePixel
     * @param xIndex
     * @param yIndex
     * @param rgbArray
     * @return A Vector value
     * MEANING
     *  This functions writes the pixel (x,y) with the color [rgb[0],rgb[1],rgb[2]]
     *************************************************/
    public void writePixel(int xIndex, int yIndex, int[] rgbArray){

        int rgb = new Color(rgbArray[0], rgbArray[1], rgbArray[2]).getRGB();
        _image.setRGB(xIndex, yIndex, rgb);

    }
    /************************************************
     * FUNCTION
     *  writePixel
     * @param xIndex
     * @param yIndex
     * @param color
     * @return void
     * MEANING
     *  This functions writes the pixel (x,y) with the given color
     *************************************************/
    public void writePixel(int xIndex, int yIndex, Color color){
        _image.setRGB(xIndex, yIndex, color.getRGB());
    }
    /*************************************************
     * FUNCTION
     *  equals
     * @param o Vector value
     * @return A boolean value
     * MEANING
     *  Function that checks if two ImageWriter are equal, if so it returns true
     **************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageWriter)) return false;
        ImageWriter that = (ImageWriter) o;
        return _imageWidth == that._imageWidth &&
                _imageHeight == that._imageHeight &&
                _Ny == that._Ny &&
                _Nx == that._Nx &&
                PROJECT_PATH.equals(that.PROJECT_PATH) &&
                _image.equals(that._image) &&
                _imageName.equals(that._imageName);
    }
}
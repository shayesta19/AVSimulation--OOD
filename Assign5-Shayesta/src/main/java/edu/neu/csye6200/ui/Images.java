package edu.neu.csye6200.ui;

import edu.neu.csye6200.av.Road;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Images {

    BufferedImage redCar;
    BufferedImage purpleCar;
    BufferedImage greyCar;
    BufferedImage truck;
    private Random random;
    private static Images _instance;
    private static final Object mutex = new Object();


    private Images() {
        try {
            random = new Random();
            redCar = ImageIO.read(getClass().getResource("/images/redSmallCar.png"));
            purpleCar = ImageIO.read(getClass().getResource("/images/purpleSmallCar.png"));
            greyCar = ImageIO.read(getClass().getResource("/images/greySmallCar.png"));
            truck = ImageIO.read(getClass().getResource("/images/truck.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Images getInstance() {
        Images result = _instance;
        if (result == null) {
            synchronized (mutex) {
                result = _instance;
                if (result == null)
                    _instance = result = new Images();
            }
        }
        return result;
    }


    public BufferedImage getImage(){
        int num = random.nextInt(4);
        switch (num) {
            case 1:
                return truck;
            case 2:
                return purpleCar;
            case 3:
                return greyCar;
            default:
                return redCar;
        }
    }

}

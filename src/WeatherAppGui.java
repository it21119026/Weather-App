import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {

    private JSONObject weatherData;

    public WeatherAppGui() {
        super("Weather app");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(450, 650);

        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);

        addGuiComponents();

    }

    private void addGuiComponents() {

        JTextField searchTextField = new JTextField();

        searchTextField.setBounds(15, 15, 351, 45);

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);


        //Weather Image

        JLabel weatherConditionImage = new JLabel(loadImage("C:\\Users\\Kolitha\\Desktop\\SHIIT\\Weather App\\src\\Assets\\cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));

        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        JLabel humidityImage = new JLabel(loadImage("C:\\Users\\Kolitha\\Desktop\\SHIIT\\Weather App\\src\\Assets\\humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b></html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        JLabel wingSpeedImage = new JLabel(loadImage("C:\\Users\\Kolitha\\Desktop\\SHIIT\\Weather App\\src\\Assets\\windspeed.png"));
        wingSpeedImage.setBounds(220, 500, 74, 66);
        add(wingSpeedImage);

        JLabel wingspeedText = new JLabel("<html><b>WingSpeed</b> 15km/h</html>");
        wingspeedText.setBounds(310, 500, 85, 55);
        wingspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(wingspeedText);

        //search Button

        JButton searchButton = new JButton(loadImage("C:\\Users\\Kolitha\\Desktop\\SHIIT\\Weather App\\src\\Assets\\search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();

                // validate input - remove whitespace to ensure non-empty text
                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                // update gui

                // update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                // depending on the condition, we will update the weather image that corresponds with the condition
                switch (weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.pngImage"));
                        break;
                }

                // update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");

                // update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                // update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                wingspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
            }
        });
        add(searchButton);
    }

    // used to create images in our gui components
    private ImageIcon loadImage(String resourcePath) {
        try {
            // read the image file from the path given
            BufferedImage image = ImageIO.read(new File(resourcePath));

            // returns an image icon so that our component can render it
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;

    }
}
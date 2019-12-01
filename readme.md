# Master - Stream Processing
## Simulation Part

This project simulates a building. <br>
The Simulation is heavily simplified and used to generate Data, we use this Data to get in touch with stream processing. See the [python program](https://github.com/ixLikro/master-stream-processing-python) for more information.

The generated data will be send via a socket connection. <br>
Every simulated Sensor has it own Socket connection.

We simulate a detached house with an off-grind photovoltaic system. <br>
The energy simulation is based on the [Photovoltaic Geographical Information System](https://re.jrc.ec.europa.eu/pvg_tools/en/tools.html) with the location Wolfenbüttel, Germany.

## Simulated Senors:
| Sensor  | Port  | Send interval <br> in second (Simulation Time)  | Description  |
|---|---|---|---|
| Sim Time Sensor  | 54000  | 1  | Send the current Simulation Time as java Timestamp  |
|Temperature Sensor| 54001  | 10 * 60  | The outside Temperature in K  |
|Barometer 1| 54002  |12 * 60|The outside air pressure in hPa|
|Barometer 2| 54003  |12 * 60|The outside air pressure in hPa|
|Barometer 3| 54004  |12 * 60|The outside air pressure in hPa|
|Air quality|54005|60|The C02 portion of the living room in ppm|
|Air quality|54006|60|The C02 portion of the bathroom in ppm|
|Air quality|54007|60|The C02 portion of the bedroom in ppm|
|Air quality|54009|60|The C02 portion of the kitchen in ppm|
|Window States|54010|10|The windows states in the living room in ppm|
|Window States|54011|10|The windows states in the bathroom in ppm|
|Window States|54012|10|The windows states in the bedroom in ppm|
|Window States|54013|10|The windows states in the kitchen in ppm|
|Energy Consumption|54014|60|The Energy Consumption of the hole Building in kW|
|Photovoltaic Produce|54015|60|The Energy that is currently produced by the photovoltaic system in kW|
|Battery state|54016|60|Send the currently stored and the maximum usable capacity in kWh|
All velures are send as JSON string

## Dynamic Settings
You can change some settings like simulation speed, window states or the current simulation time even if the simulation is currently running! <br> See [settings.conf]() for more information




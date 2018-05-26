// Select your modem:
#define TINY_GSM_MODEM_SIM800


#include <TinyGsmClient.h>
#include <PubSubClient.h>
#include <dht.h>
#define SerialMon Serial

#include <SoftwareSerial.h>
SoftwareSerial SerialAT(2, 3);

int counter = 0;

// GPRS credentials
const char apn[]  = "internet";
const char user[] = "gdata";
const char pass[] = "gdata";

// MQTT details

const char* mqttServer = "iot.eclipse.org";
const char* mqttUsername = "Vagik";
const char* mqttPassword = "446655";
const int mqttPort = 1883;



// Subscribe topics
const char* humidityCommandsTopic = "android/hum";

// Publishing topics
const char* humidityValueTopic = "arduino/hum";
const char* wateringTopic = "arduino/watering";
const char* temperatureValueTopic = "arduino/temp";

TinyGsm modem(SerialAT);
TinyGsmClient client(modem);
PubSubClient mqtt(client);

long lastReconnectAttempt = 0;
long lastRelay = 0;
long relayTimer = 0;

//Датчик температуры
#define dht_pin A1
dht DHT;
int tempValue = 0;

//Датчик влажности почвы
int humidityPin = A0;
int humidityValue = 0;

// Релейный модуль
int relayPin = 9;
bool relayOn = false;

bool autoWatering = false;
bool watering = false;



void setup() {
  SerialMon.begin(9600);
  delay(200);

  SerialAT.begin(9600);
  delay(3000);

  SerialMon.println("Initializing modem...");
  modem.init();
  initModem();
  mqtt.setServer(mqttServer, mqttPort);
  mqtt.setCallback(mqttCallback);
 
  pinMode(relayPin, OUTPUT); 
  turnPumpOff();
  
  DHT.read11(dht_pin);
  
  delay(500);
}


void loop() {
  startConnection();
  
  int hum = 100 - getHumidityValue()/7;
  String humidity = String(hum);
  String temperature = String(getTemperatureValue());
  
  
  mqtt.publish(temperatureValueTopic, temperature.c_str());
  mqtt.publish(humidityValueTopic, humidity.c_str());
  
  if(autoWatering == true){
    if(hum < 40 && relayOn == false){
      mqtt.publish(wateringTopic, "B");  //B - begin
      turnPumpOn();
    }
    if(humidityValue > 80 && relayOn == true){
      mqtt.publish(wateringTopic, "E");  //E - end
      turnPumpOff();
    }
  } else {
      if(watering == true && relayOn == false){
        turnPumpOn();
      }  
      if(watering == false && relayOn == true){
         turnPumpOff();
      } 
   }

  delay(2000);
}


//---------------------------------------------SENSORS---------------------------------------------
void turnPumpOn(){
  digitalWrite(relayPin, LOW);
  delay(100);
  relayOn = true;
} 

void turnPumpOff(){
  digitalWrite(relayPin, HIGH); 
  delay(100);
  relayOn = false;
}
int getTemperatureValue(){
  int temp = DHT.temperature;
  delay(100);
  return temp;
}

int getHumidityValue(){
  int hum = analogRead(humidityPin);
  delay(100);
  return hum;
}

//------------------------------------------------------------------------------------------------


//---------------------------------------------MQTT-----------------------------------------------
boolean mqttConnect() {
  //counter = 0;
  SerialMon.print("Connecting to ");
  SerialMon.print(mqttServer);
  if (!mqtt.connect("Vagik", mqttUsername, mqttPassword)) {
    SerialMon.println(" fail");
    return false;
  }
  SerialMon.println(" OK");
  
  mqtt.subscribe(humidityCommandsTopic);
  
  return mqtt.connected();
}

void mqttCallback(char* topic, byte* payload, unsigned int len) {

  SerialMon.print("Message arrived [");
  SerialMon.print(topic);
  SerialMon.print("]: ");
  char* mes = (char*)payload;
  SerialMon.print(mes);
  SerialMon.println();

  if(mes[0] == '0' & mes[1] == '0'){
    watering = false;
    autoWatering = false;
  } else if(mes[0] == '0' & mes[1] == '1'){
      watering = false;
      autoWatering = true;
    } else if(mes[0] == '1'){
        relayTimer = 10000*((int)mes[2]-48);
        SerialMon.println(relayTimer);
        watering = true;
        autoWatering = false;
      }
      
  
}

void startConnection(){
  if (mqtt.connected()) {
    mqtt.loop();
  } else {
    unsigned long t = millis();
    if (t - lastReconnectAttempt > 10000L) {
      lastReconnectAttempt = t;
      if (mqttConnect()) {
        lastReconnectAttempt = 0;
      }else {
         initModem();
       }
    }
  }
}

void initModem(){
  counter = 0;
  SerialMon.print("Waiting for network...");
  if (!modem.waitForNetwork()) {
    SerialMon.println(" fail");
  } else {
      SerialMon.println(" OK");

      SerialMon.print("Connecting to APN: ");
      SerialMon.print(apn);
      if (!modem.gprsConnect(apn, user, pass)) {
        SerialMon.println(" fail");
      } else{
          SerialMon.println(" OK");
        }
    }
    
  
}
//------------------------------------------------------------------------------------------------


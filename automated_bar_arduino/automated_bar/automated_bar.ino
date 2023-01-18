
#define echoPin_main_Sensor 2 // attach pin D2 Arduino to pin Echo of HC-SR04
#define trigPin_main_Sensor 3 //attach pin D3 Arduino to pin Trig of HC-SR04

#define echoPin_whiskey_Sensor 4 // attach pin D2 Arduino to pin Echo of HC-SR04
#define trigPin_whiskey_Sensor 5 //attach pin D3 Arduino to pin Trig of HC-SR04

#define IN1_Rotator_Motor 6
#define IN2_Rotator_Motor 7
#define EN_Rotator_Motor A0

#define IN1_Whiskey_Motor 8
#define IN2_Whiskey_Motor 9
#define EN_Whiskey_Motor 13

#define whiskey_order_button 10

#define whiskey_light 11

// defines variables
bool orderclicked;
long duration; // variable for the duration of sound wave travel
int distance; // variable for the distance measurement
bool Cup_Available=false;
bool order_is_done=false;


bool Whiskey_Flow(bool orderclicked,bool Cup_Available)
{
  while (orderclicked==true && Cup_Available==true)
  {
    if (ultrasonic_below_7("whiskey") == true)
    {
      analogWrite(EN_Rotator_Motor,0);
      digitalWrite(EN_Whiskey_Motor,HIGH);
      orderclicked=false;
      delay(5000);
      digitalWrite(EN_Whiskey_Motor,LOW);
      delay(500);
      analogWrite(EN_Rotator_Motor,255);

      break;
    }
  }
  while (order_is_done==false)
  {
    if (ultrasonic_below_7("main")==true)
    {
      order_is_done=true;
      analogWrite(EN_Rotator_Motor,0);
      break;
    }
  }
  return order_is_done;
}


bool ultrasonic_below_7(char ultrasonic[10])
{
  // Serial.println(ultrasonic);
  if (ultrasonic == "main")
  {
      Serial.println("main");
        // Clears the trigPin_main_Sensor condition
      digitalWrite(trigPin_main_Sensor, LOW);
      delayMicroseconds(2);
      // Sets the trigPin_main_Sensor HIGH (ACTIVE) for 10 microseconds
      digitalWrite(trigPin_main_Sensor, HIGH);
      delayMicroseconds(10);
      digitalWrite(trigPin_main_Sensor, LOW);
      // Reads the echoPin_main_Sensor, returns the sound wave travel time in microseconds
      duration = pulseIn(echoPin_main_Sensor, HIGH);
      // Calculating the distance
      distance = duration * 0.034 / 2; // Speed of sound wave divided by 2 (go and back)
      // Displays the distance on the Serial Monitor
  }

  
  if (ultrasonic == "whiskey")
  {
      Serial.println("whiskey");
        // Clears the trigPin_whiskey_Sensor condition
      digitalWrite(trigPin_whiskey_Sensor, LOW);
      delayMicroseconds(2);
      // Sets the trigPin_whiskey_Sensor HIGH (ACTIVE) for 10 microseconds
      digitalWrite(trigPin_whiskey_Sensor, HIGH);
      delayMicroseconds(10);
      digitalWrite(trigPin_whiskey_Sensor, LOW);
      // Reads the echoPin_whiskey_Sensor, returns the sound wave travel time in microseconds
      duration = pulseIn(echoPin_whiskey_Sensor, HIGH);
      // Calculating the distance
      distance = duration * 0.034 / 2; // Speed of sound wave divided by 2 (go and back)
      // Displays the distance on the Serial Monitor
  }

  if (distance<7)
  {
    Serial.print("distance is ");
    Serial.println(distance);
    return true;
  }
  else
    return false;
    
}


void setup()
{
  pinMode(IN1_Rotator_Motor,OUTPUT); 
  pinMode(IN2_Rotator_Motor,OUTPUT); 
  pinMode(EN_Rotator_Motor,OUTPUT);

  pinMode(IN1_Whiskey_Motor,OUTPUT); 
  pinMode(IN2_Whiskey_Motor,OUTPUT); 
  pinMode(EN_Whiskey_Motor,OUTPUT);

  pinMode(trigPin_main_Sensor, OUTPUT); // Sets the trigPin_main_Sensor as an OUTPUT
  pinMode(echoPin_main_Sensor, INPUT); // Sets the echoPin_main_Sensor as an INPUT

  pinMode(trigPin_whiskey_Sensor, OUTPUT); // Sets the trigPin_whiskey_Sensor as an OUTPUT
  pinMode(echoPin_whiskey_Sensor, INPUT); // Sets the echoPin_whiskey_Sensor as an INPUT

 // Buttons to order drink
  pinMode(whiskey_order_button, INPUT); // Sets the echoPin_main_Sensor as an INPUT

  // Light Indication of what have been ordered
  pinMode(whiskey_light, OUTPUT); // Sets the echoPin_main_Sensor as an INPUT
 
 // Normalize motors to move clockwise
  digitalWrite(IN1_Rotator_Motor,LOW); // Normally LOW inthis pin
  digitalWrite(IN2_Rotator_Motor,HIGH); // Normally HIGH inthis pin
  digitalWrite(IN1_Whiskey_Motor,LOW); // Normally LOW inthis pin
  digitalWrite(IN2_Whiskey_Motor,HIGH); // Normally HIGH inthis pin
  digitalWrite(whiskey_light,LOW);

  Serial.begin(9600); // // Serial Communication is starting with 9600 of baudrate speed
  orderclicked=false;
}


void loop()
{
  Cup_Available=false;
  order_is_done=false;
  orderclicked=false;
  if (digitalRead(whiskey_order_button)==HIGH)
  {
    digitalWrite(whiskey_light,HIGH);
    orderclicked=true;
  }
  Serial.println(digitalRead(whiskey_order_button));
  
  
  while (orderclicked==true)
  {
    if (ultrasonic_below_7("main")==true)
    {
      Cup_Available=true;
      analogWrite(EN_Rotator_Motor,255);
      digitalWrite(EN_Whiskey_Motor,LOW);
      Serial.println("Going into whiskey flow...");
      order_is_done=Whiskey_Flow(orderclicked,Cup_Available);
    }
    else
    {
      Serial.println("No Glass Available");
    }
    if (order_is_done==true)
    {
      digitalWrite(whiskey_light,LOW);
      break;
    }
  }




Serial.println();
// Serial.println("Order is done!");
}

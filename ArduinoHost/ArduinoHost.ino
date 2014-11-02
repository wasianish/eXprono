int compStartByte2 = 23;
int compStartByte3 = 24;

int arduStartByte1 = 32;
int arduStartByte2 = 33;
int arduStartByte3 = 34;

void setup() {
  Serial.begin(115200);
}

void loop() {
  if(Serial.available()) {
    if(Serial.read() == compStartByte2) {
      char received[2];
      Serial.readBytes(received, 2);
      execute(received);
    } else if(Serial.read() == compStartByte3) {
      char received[3];
      Serial.readBytes(received, 3);
      execute(received);
    }
  }
}

void digReadDif(int pin) {
  char out[3] = {arduStartByte2, pin, 0};
  if(digitalRead(pin) == HIGH) {
    out[2] = 1;
  }
  Serial.write(out, 3);
}
  
void digReadSame(int pin) {
  char out[2] = {arduStartByte1, pin};
  if(digitalRead(pin) == HIGH) {
    out[2] |= 10000000;
  }
  Serial.write(out, 2);
}

void anaRead8(int pin) {
  char out[3] = {arduStartByte2, pin, analogRead(pin)/4};
  Serial.write(out, 3);
}

void anaRead10(int pin) {
  int val = analogRead(pin);
  char out[4] = {arduStartByte3, pin, val & 11111111, val >> 8};
  Serial.write(out,4);
}

void portRead(int port) {
  int data;
  switch(port) {
    case 1:
      data = PINA;
      break;
    case 2:
      data = PINB;
      break;
    case 3:
      data = PINC;
      break;
    case 4:
      data = PIND;
      break;
    case 5:
      data = PINE;
      break;
    case 6:
      data = PINF;
      break;
    case 7:
      data = PING;
      break;
    case 8:
      data = PINH;
      break;
    case 9:
      data = PINJ;
      break;
    case 10:
      data = PINK;
      break;
    case 11:
      data = PINL;
      break;
  }
  char out[3] = {arduStartByte2, port, data};
  Serial.write(out, 3);
}

void portIORead(int port) {
  int data;
  switch(port) {
    case 1:
      data = DDRA;
      break;
    case 2:
      data = DDRB;
      break;
    case 3:
      data = DDRC;
      break;
    case 4:
      data = DDRD;
      break;
    case 5:
      data = DDRE;
      break;
    case 6:
      data = DDRF;
      break;
    case 7:
      data = DDRG;
      break;
    case 8:
      data = DDRH;
      break;
    case 9:
      data = DDRJ;
      break;
    case 10:
      data = DDRK;
      break;
    case 11:
      data = DDRL;
      break;
  }
  char out[3] = {arduStartByte2, port, data};
  Serial.write(out, 3);
}

void portWrite(int pin, int data) {
  switch(pin) {
    case 1:
      PORTA = data;
      break;
    case 2:
      PORTB = data;
      break;
    case 3:
      PORTC = data;
      break;
    case 4:
      PORTD = data;
      break;
    case 5:
      PORTE = data;
      break;
    case 6:
      PORTF = data;
      break;
    case 7:
      PORTG = data;
      break;
    case 8:
      PORTH = data;
      break;
    case 9:
      PORTJ = data;
      break;
    case 10:
      PORTK = data;
      break;
    case 11:
      PORTL = data;
      break;
  }
}

void portIOWrite(int pin, int data) {
  switch(pin) {
    case 1:
      DDRA = data;
      break;
    case 2:
      DDRB = data;
      break;
    case 3:
      DDRC = data;
      break;
    case 4:
      DDRD = data;
      break;
    case 5:
      DDRE = data;
      break;
    case 6:
      DDRF = data;
      break;
    case 7:
      DDRG = data;
      break;
    case 8:
      DDRH = data;
      break;
    case 9:
      DDRJ = data;
      break;
    case 10:
      DDRK = data;
      break;
    case 11:
      DDRL = data;
      break;
  }
}

void execute(char received[]) {
  int pin = received[0];
  int action = received[1];
  
  switch(action) {
    case 0: // Digital Read separate pin and value bytes
      digReadDif(pin);
      break;
    case 1: // Digital Read same pin and value byte
      digReadSame(pin);
      break;
    case 2: // Analog Read 8 bit
      anaRead8(pin);
      break;
    case 3: // Analog Read 10 bit
      anaRead10(pin);
      break;
    case 4: // Digital write high
      digitalWrite(pin, HIGH);
      break;
    case 5: // Digital write low
      digitalWrite(pin, LOW);
      break;
    case 6: // Analog write
      analogWrite(pin, received[2]);
      break;
    case 7: // Pin mode input
      pinMode(pin, INPUT);
      break;
    case 8: // Pin mode output
      pinMode(pin, OUTPUT);
      break;
    case 9: // Port Read (pin is A - L) 
      portRead(pin);
      break;
    case 10: // Port write
      portWrite(pin, received[2]);
      break;
    case 11: // Port write I/O
      portIOWrite(pin, received[2]);
      break;
  }
  
}

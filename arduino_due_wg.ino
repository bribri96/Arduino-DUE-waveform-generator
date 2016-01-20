#define USECBASE 7 //Tempo necessario per eseguire le operazioni di analogWrite nel loop
#define SAMPLE 120 //Numero si campioni che compongono il buffer di dati
#define MAXFREQ 1000 //Massima frequenza raggiungibile

int idx=0;
long buf[SAMPLE]; //Buffer dei dati da visualizzare

void setup() {
  analogWriteResolution(12); //Risoluzione DAC
#ifdef DEBUG
  Serial.begin(115200);
#endif
}

int delayusec = 1; //Ritardo necessario per modificare la frequenza

void loop() {

  if(Serial.available()){
    switch(Serial.read()){

      case's':{ //Se viene ricevuta la lettera s inizio la routine di ricezione dei dati
      
        int datanum = Serial.parseInt(); //Numero di dati da ricevere, in futuro verrà usato per un bufer di dimensioni variabile
#ifdef DEBUG
        Serial.print(datanum);
        Serial.print(":");
#endif

        for(int i = 0; i < datanum; i++){
          buf[i] = Serial.parseInt();//ricezione dei dati
#ifdef DEBUG
          Serial.print(buf[i]);
          Serial.print(":");
#endif
        }
      }
      break;

      case'f':{// Se viene ricevuta la f allora modifico la frequenza

        int freq = Serial.parseInt();//Frequenza da impostare

        if(freq > MAXFREQ){ //Controllo della frequenza massima
          freq = MAXFREQ;
        }

        double period = 1.0L / freq; //Converto la frequenza nel periodo (Hz -> s)

        double microsec = period * 100000.0L / 12.0L; //Converto il periodo da secondi a microsecondi (s -> us)

#ifdef DEBUG
        Serial.print(millis()); Serial.print("\tfrequenza : ");
        Serial.print(freq); Serial.print("Hz, periodo : ");
        Serial.print(period, 9); Serial.print("s, microsecondi : ");
        Serial.print(microsec, 9); Serial.print("us, delay : ");
        Serial.print(microsec - USECBASE, 9); Serial.println("us");
#endif

        delayusec = microsec - USECBASE; //Calcolo il ritardo da impostare per ottenere la frequenza scelta, utilizzando il tempo medio impiegato dalla funzione analogWrite
        
      break;
      }
    }
  }
  else{
    while(Serial.available())
      Serial.read();
  }
  
  analogWrite(DAC1, buf[idx]); //Mando in uscita il valore corrente

  idx++; //Incremento l'indice del buffer
  
  if(idx == SAMPLE) //Se l'indice del buffer raggiunge il suo valore massimo lo resetto
    idx = 0;

  delayMicroseconds(delayusec); //Applico il ritardo per ottenere la frequenza impostata
}

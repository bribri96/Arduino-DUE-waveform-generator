# Arduino-DUE-waveform-generator 

Come utilizzare Arduino Due per generare delle forme d'onda, per ora le forme disponibili sono :
+ Sinusoidale
+ Triangolare
+ Dente di Sega
+ Quadrata
+ Personalizzata

L'Arduino viene comandato da una GUI scritta in java su cui è possibile selezionare l'onda e la frequenza.

#Sinusodale
![screen 1](https://github.com/hkbristol/Arduino-DUE-waveform-generator/blob/master/Immagini/dac1.png) ![dso 1](https://github.com/hkbristol/Arduino-DUE-waveform-generator/blob/master/Immagini/IMAG020.BMP)
#Triangolare
![screen 2](https://github.com/hkbristol/Arduino-DUE-waveform-generator/blob/master/Immagini/dac2.png) ![dso 2](https://github.com/hkbristol/Arduino-DUE-waveform-generator/blob/master/Immagini/IMAG021.BMP)
#Dente di sega
![screen 3](https://github.com/hkbristol/Arduino-DUE-waveform-generator/blob/master/Immagini/dac3.png) ![dso 3](https://github.com/hkbristol/Arduino-DUE-waveform-generator/blob/master/Immagini/IMAG022.BMP)
#Quadra
![screen 4](https://github.com/hkbristol/Arduino-DUE-waveform-generator/blob/master/Immagini/dac4.png) ![dso 4](https://github.com/hkbristol/Arduino-DUE-waveform-generator/blob/master/Immagini/IMAG023.BMP)
#Personalizzata
![screen 5](https://github.com/hkbristol/Arduino-DUE-waveform-generator/blob/master/Immagini/dac5.png) ![dso 5](https://github.com/hkbristol/Arduino-DUE-waveform-generator/blob/master/Immagini/IMAG025.BMP)

#Caratteristiche
+ Frequenza 1-1000Hz
+ Vpp 2,20V
+ Vdc 0,55V

N.B.
L'uscita può essere condizionata tramite un amplificatore differenziale per portarla ad un range di 0-3,3V

La frequenza di uscita massima sembra essere fortemente condizionata dal tempo impiegato da ,,, analogWrite ,,, Con più studi sul DAC dell'ATSAM3X8E penso si posaa migliorare di molto questo parametro

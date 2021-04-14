import { Component, Input, OnInit } from '@angular/core';
import Speech from 'speak-tts';

@Component({
  selector: 'app-text-speech',
  templateUrl: './text-speech.component.html',
  styleUrls: ['./text-speech.component.css'],
})
export class TextSpeechComponent implements OnInit {
  vol: number;
  first: number;
  speaking: boolean;
  paused: boolean;
  supported: boolean;
  @Input() description: string;
  speech = new Speech();
  textDescription: String =
    'One Night @ the Call Center is a novel written by Chetan Bhagat and first published in 2005. The novel revolves around a group of six call center employees working at the Connexions call center in Gurgaon, Haryana, India. It takes place during one night, during which all of the leading characters confront some aspect of themselves or their lives they would like to change. The story uses a literal deus ex machina, when the characters receive a phone call from God. The book was the second best-selling novel from the author after Five Point Someone.';

  constructor() {
    this.speech = new Speech(); // will throw an exception if not browser supported
    if (this.speech.hasBrowserSupport()) {
      // returns a boolean
      console.log('speech synthesis supported');
      this.speech
        .init({
          volume: 1,
          lang: 'en-GB',
          rate: 1,
          pitch: 1,
          voice: 'Google UK English Male',
          splitSentences: true,
          listeners: {
            onvoiceschanged: (voices) => {
              console.log('Event voiceschanged');
            },
          },
        })
        .then((data) => {
          // The "data" object contains the list of available voices and the voice synthesis params
          console.log('Speech is ready, voices are available');
        })
        .catch((e) => {
          this.supported = false;
          console.error('An error occured while initializing : ', e);
        });
    }
  }

  ngOnInit(): void {
    this.vol = 1;
    this.first = 1;
    this.speaking = false;
    this.supported = true;
  }

  startAudio() {
    this.vol = 0;
    this.first = 0;
    this.speaking = true;
    this.speech
      .speak({
        text: this.description,
      })
      .then(() => {
        console.log('Success !');
      })
      .catch((e) => {
        this.supported = false;
        console.error('An error occurred :', e);
      });
  }

  stopAudio() {
    this.vol = 1;
    this.first = 1;
    this.speaking = false;
    this.speech.cancel();
  }

  pauseAudio() {
    this.paused = true;
    this.speech.pause();
  }

  resumeAudio() {
    this.paused = false;
    this.speech.resume();
  }
}

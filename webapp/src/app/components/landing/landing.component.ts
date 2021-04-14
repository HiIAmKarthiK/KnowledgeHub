import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css'],
  animations: [
    trigger(
      'fade',

      [
        state('void', style({ opacity: 0 })),
        transition('void => *', [animate(2000)]),
      ]
    ),
  ],
})
export class LandingComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
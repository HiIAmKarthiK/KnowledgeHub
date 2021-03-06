import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TextSpeechComponent } from './text-speech.component';

describe('TextSpeechComponent', () => {
  let component: TextSpeechComponent;
  let fixture: ComponentFixture<TextSpeechComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TextSpeechComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TextSpeechComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

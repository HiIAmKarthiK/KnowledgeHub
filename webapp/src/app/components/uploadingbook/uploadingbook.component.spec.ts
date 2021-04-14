import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadingbookComponent } from './uploadingbook.component';

describe('UploadingbookComponent', () => {
  let component: UploadingbookComponent;
  let fixture: ComponentFixture<UploadingbookComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadingbookComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadingbookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

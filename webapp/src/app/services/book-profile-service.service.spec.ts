import { TestBed } from '@angular/core/testing';

import { BookProfileServiceService } from './book-profile-service.service';

describe('BookProfileServiceService', () => {
  let service: BookProfileServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookProfileServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

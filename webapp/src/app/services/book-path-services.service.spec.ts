import { TestBed } from '@angular/core/testing';

import { BookPathServicesService } from './book-path-services.service';

describe('BookPathServicesService', () => {
  let service: BookPathServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookPathServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

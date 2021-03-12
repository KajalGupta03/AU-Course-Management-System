import { TestBed } from '@angular/core/testing';

import { AllcoursesService } from './allcourses.service';

describe('AllcoursesService', () => {
  let service: AllcoursesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AllcoursesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

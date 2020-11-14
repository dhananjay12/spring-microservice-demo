import { TestBed } from '@angular/core/testing';

import { ReactiveService } from './reactive.service';

describe('ReactiveService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ReactiveService = TestBed.get(ReactiveService);
    expect(service).toBeTruthy();
  });
});

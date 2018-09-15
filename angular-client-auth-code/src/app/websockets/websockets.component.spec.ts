import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WebsocketsComponent } from './websockets.component';

describe('WebsocketsComponent', () => {
  let component: WebsocketsComponent;
  let fixture: ComponentFixture<WebsocketsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WebsocketsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WebsocketsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

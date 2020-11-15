import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WebsocketComponent } from './websocket.component';

describe('WebsocketComponent', () => {
  let component: WebsocketComponent;
  let fixture: ComponentFixture<WebsocketComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WebsocketComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WebsocketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

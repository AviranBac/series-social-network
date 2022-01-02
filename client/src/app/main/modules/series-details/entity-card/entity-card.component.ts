import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-entity-card',
  templateUrl: './entity-card.component.html',
  styleUrls: ['./entity-card.component.less']
})
export class EntityCardComponent<T> {
  @Input() imageSrc: string;
  @Input() name: string;
  @Input() overview: string;

  constructor() { }
}

import {Component, Input} from '@angular/core';
import {TvEpisode} from "../../../shared/models/tv-episode";
import {TvSeason} from "../../../shared/models/tv-season";

@Component({
  selector: 'app-entity-card',
  templateUrl: './entity-card.component.html',
  styleUrls: ['./entity-card.component.less']
})
export class EntityCardComponent {
  @Input() entity: TvSeason | TvEpisode;
  @Input() imageSrc: string;
  @Input() name: string;
  @Input() overview: string;

  constructor() { }
}

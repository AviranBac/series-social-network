import {Component, Input} from '@angular/core';
import {TvSeason} from "../../../shared/models/tv-season";
import {TvEpisode} from "../../../shared/models/tv-episode";

@Component({
  selector: 'app-season-details',
  templateUrl: './season-details.component.html',
  styleUrls: ['./season-details.component.less']
})
export class SeasonDetailsComponent {
  @Input() season: TvSeason;

  constructor() { }

  getEpisodeDisplayName(episode: TvEpisode): string {
    return `Episode ${episode.episodeNumber}: ${episode.name}`
  }
}

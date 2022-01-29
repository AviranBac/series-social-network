export enum SeriesStatus {
  CANCELED = 'Canceled',
  ENDED = 'Ended',
  RETURNING_SERIES = 'On Air'
}

export function getStatusValue(status: string): SeriesStatus {
  return SeriesStatus[status as unknown as keyof typeof SeriesStatus]
}

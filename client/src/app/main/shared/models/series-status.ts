export enum SeriesStatus {
  CANCELLED = "Cancelled",
  ENDED = "Ended",
  RETURNING_SERIES = 'On Air'
}

export function getStatusValue(status: string) {
  return SeriesStatus[status as unknown as keyof typeof SeriesStatus]
}

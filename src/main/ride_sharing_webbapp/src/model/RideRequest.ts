import {User} from "./User";

export interface RideRequest {
  id: number;
  title: string;
  description: string;
  user: User;
}

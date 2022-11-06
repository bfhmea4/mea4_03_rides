import {User} from "./User";

export interface RideOffer {
  id: number;
  title: string;
  description: string;
  user: User;
}

import {writable} from "svelte/store";

export const MARKS = writable(Array(0));
export const PEOPLE = writable(Array(0));
export const SUBJECTS = writable(Array(0));
export const GROUPS = writable(Array(0));

export const USER = writable(null);
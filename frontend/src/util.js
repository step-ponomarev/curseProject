export const DOMAIN = '';//http://localhost:8080

export async function GET(url, opts = null) {
    const response = await fetch(url, opts);

    if (response.ok) {
        return (await response.json());
    }
}

export async function POST(url, opts) {
    const response = await fetch(url, opts);

    if (response.ok) {
        return (await response.json());
    }
}

export function getDisplayedName(person) {
    return `${person.lastname} ${person.firstname.charAt(0)}. ${person.pathername.charAt(0)}.`;
}
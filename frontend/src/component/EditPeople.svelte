<script>
    import {POST, getDisplayedName, DOMAIN, GET} from '../util.js';
    import {MARKS, PEOPLE, GROUPS} from '../store.js';
    import Select, {Option} from '@smui/select';
    import Textfield, {Input, Textarea} from '@smui/textfield';
    import Button, {Group, GroupItem, Label, Icon} from '@smui/button';

    const ROLES = ['STUDENT', 'TEACHER'];

    let firstname_ADD = '';
    let lastname_ADD = '';
    let pathername_ADD = '';
    let role_ADD = ROLES[0];
    let group_ADD = $GROUPS.length === 0 ? null : JSON.stringify($GROUPS[0]);


    let personId_EDIT = $PEOPLE.length === 0 ? null : $PEOPLE[0].id;
    let savedPersonId = null;

    let firstname_EDIT = '';
    let lastname_EDIT = '';
    let pathername_EDIT = '';
    let groupJson_EDIT = $GROUPS.length === 0 ? null : JSON.stringify($GROUPS[0]);
    let role_EDIT = personId_EDIT != null ? $PEOPLE.find(person => person.id == personId_EDIT).role : null


    GROUPS.subscribe(group => {
        group_ADD = $GROUPS.length === 0 ? null : JSON.stringify($GROUPS[0]);
        groupJson_EDIT = $GROUPS.length === 0 ? null : JSON.stringify($GROUPS[0]);
    });

    $: {
        const person = $PEOPLE.length !== 0 ? $PEOPLE.find(person => personId_EDIT == person.id) : null;

        if (person != null) {
            if (savedPersonId != person.id) {
                firstname_EDIT = person.firstname;
                lastname_EDIT = person.lastname;
                pathername_EDIT = person.pathername;
                role_EDIT = person.role;
                savedPersonId = person.id;
            }
        }
    }

    async function addPerson(e) {
        e.preventDefault();
        const person = {
            firstname: firstname_ADD,
            lastname: lastname_ADD,
            pathername: pathername_ADD,
            role: role_ADD,
            group: await JSON.parse(group_ADD)
        };

        await POST(DOMAIN + '/person', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(person)
        }).then(result => {
            $PEOPLE.push(result);
            PEOPLE.set($PEOPLE.slice());

            firstname_ADD = '';
            lastname_ADD = '';
            pathername_ADD = '';

            personId_EDIT = $PEOPLE.length === 1 ? $PEOPLE[0].id : personId_EDIT;
        });
    }

    async function updatePerson() {
        const person = {
            id: personId_EDIT,
            firstname: firstname_EDIT,
            lastname: lastname_EDIT,
            pathername: pathername_EDIT,
            role: role_EDIT,
            group: JSON.parse(groupJson_EDIT)
        };

        await POST(DOMAIN + '/person', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(person)
        }).then(result => {
            PEOPLE.set($PEOPLE.map(person => {
                if (person.id === result.id) {
                    return result;
                } else {
                    return person;
                }
            }));
        });
    }

    async function deletePerson(e) {
        e.preventDefault();
        const deletedPerson = await $PEOPLE.find(person => person.id == personId_EDIT);

        const response = await fetch(DOMAIN + '/person', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(deletedPerson)
        });

        if (response.ok) {
            PEOPLE.set(await $PEOPLE.filter(person => person.id !== deletedPerson.id));
            personId_EDIT = $PEOPLE.length === 0 ? null : $PEOPLE[0].id;

            if (deletedPerson.role === "TEACHER") {
                MARKS.set($MARKS.filter(filter => filter.teacher.id !== deletedPerson.id));
            } else {
                MARKS.set($MARKS.filter(filter => filter.student.id !== deletedPerson.id));
            }
        }
    }
</script>

<div class="container">
    <div class="addBlock block">
        <div class="title">
            Add Person
        </div>

        <form class="col" on:submit|preventDefault={addPerson}>
            <Textfield required bind:value={firstname_ADD}
                       label="Firstname"
                       type="text"

            />

            <Textfield required bind:value={lastname_ADD}
                       label="Lastname"
                       type="text"

            />

            <Textfield required bind:value={pathername_ADD}
                       label="Pathername"
                       type="text"

            />

            <Select bind:value={role_ADD}
                    label="Role"
                    required
                    enhanced
                    class="role-select-width"
                    menu$class="role-select-width">
                {#each ROLES as role}
                    <Option value={role} selected={role_ADD === role}>{role}</Option>
                {/each}
            </Select>

            {#if $GROUPS.length !== 0}
                <Select bind:value={group_ADD}
                        label="Group"
                        enhanced
                        required
                        class="group-select-width"
                        menu$class="group-select-width">
                    {#each $GROUPS.slice() as group}
                        <Option value={JSON.stringify(group)} selected={JSON.parse(group_ADD).id == group.id}>{group
                        .name}</Option>
                    {/each}
                </Select>
            {/if}

            <Button on:click={addPerson}><Label>Добавить</Label></Button>
        </form>
    </div>

    {#if $PEOPLE.length !== 0}
        <div class="editBlock block">
            <div class="title">
                Edit Person
            </div>

            <form class="col" on:submit={updatePerson}>

                <Select bind:value={personId_EDIT}
                        label="Person"
                        required enhanced
                        class="person-select-width"
                        menu$class="person-select-width">
                    {#each $PEOPLE as person}
                        <Option value={person.id} selected={personId_EDIT == person.id}>{getDisplayedName(person)
                        }</Option>
                    {/each}
                </Select>

                <Textfield required bind:value={firstname_EDIT}
                           label="Firstname"
                           type="text"

                />

                <Textfield required bind:value={lastname_EDIT}
                           label="Lastname"
                           type="text"

                />

                <Textfield required bind:value={pathername_EDIT}
                           label="Pathername"
                           type="text"

                />


                <div class="field">
                    <Select bind:value={role_EDIT}
                            label="Role"
                            required
                            enhanced
                            class="role-select-width"
                            menu$class="role-select-width">
                        {#each ROLES as role}
                            <Option value={role} selected={role_EDIT === role}>{role}</Option>
                        {/each}
                    </Select>
                </div>

                {#if $GROUPS.length !== 0}
                    <div class="field">
                        <Select bind:value={groupJson_EDIT}
                                label="Group"
                                enhanced
                                required
                                class="group-select-width"
                                menu$class="group-select-width">
                            {#each $GROUPS.slice() as group}
                                <Option value={JSON.stringify(group)}
                                        selected={JSON.parse(groupJson_EDIT).id == group.id}>{group
                                .name}</Option>
                            {/each}
                        </Select>
                    </div>
                {/if}


                <Button on:click={updatePerson}><Label>Изменить</Label></Button>
                <Button on:click={deletePerson}><Label>Удалить</Label></Button>
            </form>

        </div>
    {/if}
</div>


<style>
    * :global(select, .role-select-width) {
        width: 200px;
    }

    * :global(select, .group-select-width) {
        width: 200px;
    }

    * :global(select, .person-select-width) {
        width: 250px;
    }

    #currentPerson {
        z-index: 200;
    }

    .select {
        width: 200px;
        height: 40px;
        z-index: 200;
        box-sizing: border-box;
    }

    .container {
        display: grid;
        gap: 1em 1em;
        align-items: start;
        grid-auto-flow: column;
        box-shadow: 0px 0px 3px 3px rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        padding: 1em;
        width: max-content;
        height: max-content;
        margin-right: auto;
        margin-left: auto;
    }

    .block {
        display: grid;
        justify-content: start;
        gap: 1em;
        box-sizing: border-box;
    }

    .title {
        font-size: 18px;
    }

    .row {
        display: grid;
        justify-content: flex-start;
        grid-auto-flow: column;
        gap: 0 0.5em;
        width: 100%;

        box-sizing: border-box;
    }

    .col {
        display: grid;
        justify-content: start;
        align-content: start;
        gap: 0.5em;
        box-sizing: border-box;
    }

</style>
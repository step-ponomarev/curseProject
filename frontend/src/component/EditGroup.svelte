<script>
    import {POST, getDisplayedName, DOMAIN, GET} from '../util.js';
    import {PEOPLE, GROUPS} from '../store.js';
    import Textfield, {Input, Textarea} from '@smui/textfield';
    import Button, {Group, GroupItem, Label, Icon} from '@smui/button';
    import Select, {Option} from '@smui/select';
    import {onMount} from 'svelte';

    let group_ADD = '';

    let savedGroupId = '';
    let groupId_EDIT = $GROUPS.length === 0 ? '' : $GROUPS[0].id;
    let GROUP_EDIT = '';

    $: {
        if ($GROUPS.length !== 0) {
            const newSubject = $GROUPS.find(subject => groupId_EDIT == subject.id);

            if ((savedGroupId != groupId_EDIT)) {
                GROUP_EDIT = newSubject.name;
                savedGroupId = groupId_EDIT;
            }
        }
    }

    async function addGroup(e) {
        const group = {
            name: group_ADD
        }

        await fetch(DOMAIN + '/group', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: await JSON.stringify(group)

        }).then(result => {
            $GROUPS.push(result.json());
            GROUPS.set($GROUPS.slice());

            groupId_EDIT = $GROUPS.length === 1 ? $GROUPS[0].id : groupId_EDIT;

            group_ADD = '';
        });
    }

    async function updateGroup() {
        const group = {
            id: groupId_EDIT,
            name: GROUP_EDIT
        };


        await POST(DOMAIN + '/group', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(group)
        }).then(result => {
            const newGroups = $GROUPS.map(group => {
                if (group.id == result.id) {
                    return result;
                }
            });

            GROUPS.set(newGroups);
        });
    }

    async function deleteGroup(e) {

        const group = {
            id: groupId_EDIT,
            name: GROUP_EDIT
        };

        const response = fetch(DOMAIN + '/group', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(group)
        });

        response.then(response => {
            if (response.ok) {
                GROUPS.set($GROUPS.filter(group => group.id != groupId_EDIT));
                groupId_EDIT = $GROUPS.length === 0 ? '' : $GROUPS[0].id;
            }
        });
    }

</script>

<div class="container">
    <div class="AddBlock Block">

        <form class="col">
            <div class="title">
                Add Group
            </div>

            <Textfield required bind:value={group_ADD}
                       label="Name"
                       type="text"

            />

            <Button on:click={addGroup}><Label>Добавить</Label></Button>
        </form>
    </div>

    {#if $GROUPS.length !== 0}
        <div class="EditBlock Block">
            <form class="col">
                <div class="title">
                    Edit Group
                </div>


                <Select bind:value={groupId_EDIT}
                        label="Group"
                        on
                        enhanced
                        required
                        class="group-select-width"
                        menu$class="group-select-width">
                    {#each $GROUPS.slice() as group}
                        <Option value={group.id} selected={groupId_EDIT == group.id}>{group
                        .name}</Option>
                    {/each}
                </Select>

                <Textfield required bind:value={GROUP_EDIT}
                           label="Name"
                           type="text"
                />

                <Button on:click={updateGroup}><Label>Изменить</Label></Button>
                <Button on:click={deleteGroup}><Label>Удалить</Label></Button>
            </form>
        </div>

    {/if}

</div>

<style>
    .container {
        padding: 1em;
        display: grid;
        grid-auto-flow: column;
        align-items: start;
        justify-content: start;
        gap: 1em 1em;
        box-shadow: 0px 0px 3px 3px rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        width: max-content;
        height: max-content;
        margin-right: auto;
        margin-left: auto;
    }

    .title {
        font-size: 18px;
    }

    .col {
        display: grid;
        justify-content: start;
        align-items: start;
        gap: 1em;
        box-sizing: border-box;
    }
</style>

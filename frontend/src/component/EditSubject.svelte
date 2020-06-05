<script>
    import {MARKS, GROUPS, SUBJECTS, PEOPLE} from '../store.js';
    import {POST, getDisplayedName, DOMAIN, GET} from '../util.js';
    import Select, {Option} from '@smui/select';
    import Textfield, {Input, Textarea} from '@smui/textfield';
    import Button, {Group, GroupItem, Label, Icon} from '@smui/button';

    let subject_NAME = '';

    let savedSubjectId = '';
    let SubjectID_EDIT = $SUBJECTS.length === 0 ? '' : ($SUBJECTS[0].id);

    let SUBJECT_EDIT = '';

    $: {
        if ($SUBJECTS.length !== 0) {
            const newSubject = $SUBJECTS.find(subject => SubjectID_EDIT == subject.id);

            if (SUBJECT_EDIT != null && (savedSubjectId != SubjectID_EDIT)) {
                SUBJECT_EDIT = newSubject.name;
                savedSubjectId = SubjectID_EDIT;
            }
        }
    }

    async function addSubject() {
        const subject = {
            name: subject_NAME
        }

        fetch(DOMAIN + "/subject", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(subject)
        }).then(result => {
            if (result.ok) {
                const obj = result.json();

                $SUBJECTS.push(obj);
                SUBJECTS.set($SUBJECTS.slice());

                SubjectID_EDIT = $SUBJECTS.length === 1 ? $SUBJECTS[0].id : SubjectID_EDIT;

                subject_NAME = '';
            }
        });
    }

    async function updateSubject() {
        const newSubject = {
            id: SubjectID_EDIT,
            name: SUBJECT_EDIT,
        }

        await fetch(DOMAIN + '/subject', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(newSubject),
        }).then(result => {
            if (result.ok) {
                const obj = result.json();

                const newSubjects = $SUBJECTS.map(subject => {
                    if (subject.id === obj.id) {
                        return obj;
                    } else {
                        return subject;
                    }
                });

                SUBJECTS.set(newSubjects);
            }
        });
    }

    async function deleteSubject() {
        const deletedSubject = {
            id: SubjectID_EDIT,
            name: SUBJECT_EDIT,
        }

        fetch(DOMAIN + '/subject', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(deletedSubject)
        }).then(response => {
            if (response.ok) {
                SUBJECTS.set($SUBJECTS.filter(subject => subject.id != SubjectID_EDIT));
                SubjectID_EDIT = $SUBJECTS.length === 0 ? null : $SUBJECTS[0].id;

                MARKS.set($MARKS.filter(filter => filter.subject.id != SubjectID_EDIT));
            }
        });
    }
</script>

<div class="container">
    <div class="addBlock">
        <form class="col">
            <div class="title">
                Add Subject
            </div>
            <Textfield required bind:value={subject_NAME}
                       label="Name"
                       type="text"
            />
            <Button on:click={addSubject}><Label>Добавить</Label></Button>
        </form>
    </div>

    {#if $SUBJECTS.length !== 0}
        <div class="editBlock">
            <form class="col">
                <div class="title">
                    Edit Subject
                </div>

                <Select bind:value={SubjectID_EDIT}
                        label="Subject"
                        enhanced
                        required
                        class="group-select-width"
                        menu$class="group-select-width">
                    {#each $SUBJECTS.slice() as subject}
                        <Option value={subject.id} selected={SubjectID_EDIT == subject.id}>{subject
                        .name}</Option>
                    {/each}
                </Select>

                <Textfield required bind:value={SUBJECT_EDIT}
                           label="Name"
                           type="text"

                />
                <Button on:click={updateSubject}><Label>Изменить</Label></Button>
                <Button on:click={deleteSubject}><Label>Удалить</Label></Button>
            </form>

        </div>
    {/if}
</div>


<style>
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

    .col {
        display: grid;
        justify-content: start;
        align-content: start;
        gap: 1em;
        box-sizing: border-box;
    }
</style>
<script>
    import {MARKS, GROUPS, SUBJECTS, PEOPLE} from '../store.js';
    import Select, {Option} from '@smui/select';
    import Textfield, {Input, Textarea} from '@smui/textfield';
    import Button, {Group, GroupItem, Label, Icon} from '@smui/button';
    import {POST, GET, DOMAIN, getDisplayedName} from '../util.js';

    let VALUE = '';

    let savedMarkID = '';
    let markID = $MARKS.length === 0
            ? null
            : $MARKS[0].id;

    let studentID = $PEOPLE.filter(person => person.role == 'STUDENT').length === 0
            ? null
            : $PEOPLE.filter(person => person.role == 'STUDENT')[0].id;

    let teacherID = $PEOPLE.filter(person => person.role == 'TEACHER').length === 0
            ? null
            : $PEOPLE.filter(person => person.role == 'TEACHER')[0].id;

    let subjectID = $SUBJECTS.length === 0
            ? null
            : $SUBJECTS[0].id;

    $: {
        if (savedMarkID != markID) {
            VALUE = $MARKS.length === 0
                    ? null
                    : $MARKS.find(mark => mark.id == markID).value;

            studentID = $PEOPLE.filter(person => person.role == 'STUDENT').length === 0
                    ? null
                    : $PEOPLE.filter(person => person.role == 'STUDENT')[0].id;

            teacherID = $PEOPLE.filter(person => person.role == 'TEACHER').length === 0
                    ? null
                    : $PEOPLE.filter(person => person.role == 'TEACHER')[0].id;

            subjectID = $SUBJECTS.length === 0
                    ? null
                    : $SUBJECTS[0].id;

            savedMarkID = markID;
        }
    }

    async function updateMark() {
        const updatedMark = {
            id: markID,
            student: $PEOPLE.find(person => person.id == studentID),
            teacher: $PEOPLE.find(person => person.id == teacherID),
            subject: $SUBJECTS.find(subject => subject.id == subjectID),
            value: VALUE
        }

        await POST(DOMAIN + '/mark', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(updatedMark)
        }).then(updatedMark => {
            MARKS.set($MARKS.map(mark => {
                if (mark.id === updatedMark.id) {
                    return updatedMark;
                } else {
                    return mark;
                }
            }));
        });
    }

    async function deleteMark(e) {
        const deletedMark = {
            id: markID,
            student: $PEOPLE.find(person => person.id == studentID),
            teacher: $PEOPLE.find(person => person.id == teacherID),
            subject: $SUBJECTS.find(subject => subject.id == subjectID),
            value: VALUE
        };

        await fetch(DOMAIN + '/mark', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(deletedMark)
        }).then(reusln => {
            MARKS.set($MARKS.filter(mark => mark.id != markID));
            markID = $MARKS.length === 0
                    ? null
                    : $MARKS[0].id;

            if (markID == null) {
                document.location.pathname = '/';
            }
        });
    }
</script>

{#if $MARKS.length !== 0}

    <div class="container">
        <form class="col" on:submit|preventDefault={updateMark}>
            <div class="title">Edit Mark</div>
            <Select bind:value={markID}
                    label="Mark"
                    enhanced
                    required
                    class="mark-select-width"
                    menu$class="mark-select-width">
                {#each $MARKS.slice() as mark}
                    <Option value={mark.id} selected={markID == mark.id}>{
                    `${getDisplayedName(mark.student)}
                ${getDisplayedName(mark.teacher)}
                ${mark.value}`}</Option>
                {/each}
            </Select>

            <Select bind:value={studentID}
                    label="Student"
                    enhanced
                    required
                    class="mark-select-width"
                    menu$class="mark-select-width">
                {#each $PEOPLE.filter(person => person.role === 'STUDENT') as student}
                    <Option value={student.id} selected={studentID == student.id}>{getDisplayedName(student)}</Option>
                {/each}
            </Select>

            <Select bind:value={teacherID}
                    label="Teacher"
                    enhanced
                    required
                    class="mark-select-width"
                    menu$class="mark-select-width">
                {#each $PEOPLE.filter(person => person.role === 'TEACHER') as teacher}
                    <Option value={teacher.id} selected={teacherID == teacher.id}>{getDisplayedName(teacher)}</Option>
                {/each}
            </Select>

            <Select bind:value={subjectID}
                    label="Subject"
                    enhanced
                    required
                    class="mark-select-width"
                    menu$class="mark-select-width">
                {#each $SUBJECTS.slice() as subject}
                    <Option value={subject.id} selected={subjectID == subject.id}>{subject
                    .name}</Option>
                {/each}
            </Select>

            <Textfield required bind:value={VALUE}
                       label="Value"
                       type="number"

            />

            <Button on:click={updateMark}><Label>Изменить</Label></Button>
            <Button on:click={deleteMark}><Label>Удалить</Label></Button>
        </form>
    </div>


{/if}

<style>
    * :global(select, .role-select-width) {
        width: 400px;
    }

    * :global(select, .group-select-width) {
        width: 400px;
    }

    .container {
        display: grid;
        grid-auto-flow: column;
        align-items: start;
        justify-content: start;
        gap: 1em 1em;
        box-shadow: 0px 0px 3px 3px rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        padding: 1em;
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
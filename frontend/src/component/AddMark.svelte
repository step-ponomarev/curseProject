<script>
    import {MARKS, GROUPS, SUBJECTS, PEOPLE} from '../store.js';
    import Select, {Option} from '@smui/select';
    import Textfield, {Input, Textarea} from '@smui/textfield';
    import Button, {Group, GroupItem, Label, Icon} from '@smui/button';
    import {POST, DOMAIN, getDisplayedName} from '../util.js';

    let VALUE = '';

    let savedStudentId = null;
    let student_ID = $PEOPLE.filter(person => person.role === 'STUDENT').length === 0
            ? null
            : $PEOPLE.filter(person => person.role === 'STUDENT')[0].id;


    let savedTeacherId = null;
    let teacher_ID = $PEOPLE.filter(person => person.role === 'TEACHER').length === 0
            ? null
            : $PEOPLE.filter(person => person.role === 'TEACHER')[0].id;

    let savedSubjectId = null;
    let subject_ID = $SUBJECTS.length === 0
            ? null
            : $SUBJECTS[0].id;

    async function addMark(e) {
        e.preventDefault();
        const newMark = {
            student: $PEOPLE.find(person => person.id == student_ID),
            subject: $SUBJECTS.find(subject => subject.id == subject_ID),
            teacher: $PEOPLE.find(person => person.id == teacher_ID),
            value: VALUE
        }

        await POST(DOMAIN + '/mark', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(newMark)
        }).then(result => {
            $MARKS.push(result);
            MARKS.set($MARKS.slice());
        });

        VALUE = '';
    }

</script>

<div class="container">
    <form on:submit={addMark}>
        <Select bind:value={student_ID}
                label="Student"
                required
                enhanced
                class="role-select-width"
                menu$class="role-select-width">
            {#each $PEOPLE.filter(person => person.role === 'STUDENT') as student}
                <Option value={student.id} selected={student_ID == student.id}>{getDisplayedName(student)}</Option>
            {/each}
        </Select>

        <Select bind:value={teacher_ID}
                label="Teacher"
                required
                enhanced
                class="role-select-width"
                menu$class="role-select-width">
            {#each $PEOPLE.filter(person => person.role === 'TEACHER') as teacher}
                <Option value={teacher.id} selected={teacher_ID == teacher.id}>{getDisplayedName(teacher)}</Option>
            {/each}
        </Select>

        <Select bind:value={subject_ID}
                label="Subject"
                required
                enhanced
                class="role-select-width"
                menu$class="role-select-width">
            {#each $SUBJECTS.slice() as subject}
                <Option value={subject.id} selected={subject_ID == subject.id}>{subject.name}</Option>
            {/each}
        </Select>

        <Textfield required
                   bind:value={VALUE}
                   label="Value"
                   type="number"

        />
        <Button on:click="{addMark}"><Label>Добавить</Label></Button>
    </form>
</div>

<style>
    .container {
        display: grid;
        grid-auto-flow: column;
        gap: 0 1em;
        width: 100%;
        height: 100%;
        box-sizing: border-box;
        align-items: center;
    }

    form {
        display: grid;
        grid-auto-flow: column;
        justify-content: left;
        gap: 0 1em;
        align-items: end;
    }

</style>
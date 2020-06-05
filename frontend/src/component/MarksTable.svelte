<script>
    import {MARKS, GROUPS, SUBJECTS, PEOPLE} from '../store.js';
    import {POST, DOMAIN, getDisplayedName} from '../util.js';

</script>

<div class="container">
    <div class="row title">
        <div class="student-width">Студент</div>
        <div class="teacher-width">Учитель</div>
        <div class="subject-width">Предмет</div>
        <div class="value-width">Оценка</div>
    </div>
    {#each $MARKS.slice() as mark}
        <div class="row bordered">
            <div class="student-width">{getDisplayedName(mark.student)}</div>
            <div class="teacher-width">{getDisplayedName(mark.teacher)}</div>
            <div class="subject-width">{mark.subject.name}</div>
            <div class="value-width">{mark.value}</div>
        </div>
    {/each}
</div>


<style>
    .container {

        box-sizing: border-box;
        display: grid;
        margin-right: auto;
        margin-left: auto;
        gap: 0.5em;
        min-width: 58em;
    }

    .row {
        display: flex;
        width: 100%;
        box-sizing: border-box;
    }

    .bordered  {
        padding-top: 0.5em;
        padding-bottom: 0.5em;
        border-bottom: 1px dotted #bfbfbf;
        border-top: 1px dotted #bfbfbf;
    }

    .student-width {
        width: 30%;
    }

    .teacher-width {
        width: 30%;
    }

    .subject-width {
        width: 30%;
    }

    .value-width {
        width: 10%;
    }

</style>
<script>
    import AddMark from '../component/AddMark.svelte';
    import MarkTable from '../component/MarksTable.svelte';
    import {MARKS, GROUPS, SUBJECTS, PEOPLE} from '../store.js';

</script>

<div class="container">
    {#if
    $PEOPLE.filter(person => person.role == "TEACHER").length !== 0 &
    $PEOPLE.filter(person => person.role == "STUDENT").length !== 0 &
    $SUBJECTS.length !== 0}
        <div class="addMark">
            <AddMark/>
        </div>

        {#if $MARKS.length !== 0}
            <div class="table">
                <MarkTable/>
            </div>
        {/if}

    {:else}
        <div class="warning"> Недостаточно данных</div>
    {/if}

</div>


<style>
    .container {
        display: grid;
        gap: 2em 1em;
        align-content: start;

        width: 100%;
        height: 100%;
        box-sizing: border-box;
    }

    .title {
        font-size: 18px;
        margin-right: auto;
        margin-left: auto;
    }

    .table {
        display: grid;
        width: 100%;
        box-sizing: border-box;
    }

    .warning {
        margin-left: auto;
        margin-right: auto;
    }

    .addMark {
        margin-left: auto;
        margin-right: auto;
    }

</style>

import { AbstractControl, FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";
export function dateValidator(startDate: string, endDate: string) {
    return (formGroup: FormGroup) => {
        const startDateCtrl = formGroup.controls[startDate];
        const endDateCtrl = formGroup.controls[endDate];
        if (endDateCtrl.errors && !endDateCtrl.errors.invalidDate) {
            return;
        }
        if (startDateCtrl.value > endDateCtrl.value) {
            endDateCtrl.setErrors({ invalidDate: true });
        } else {
            endDateCtrl.setErrors(null);
        }
    }
}
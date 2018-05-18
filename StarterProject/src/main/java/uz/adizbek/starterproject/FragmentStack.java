package uz.adizbek.starterproject;

class FragmentStack {
    public final BaseFragment in;
    public final BaseFragment out;

    public FragmentStack(BaseFragment in, BaseFragment out) {
        this.in = in;
        this.out = out;
    }
}

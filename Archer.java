class Archer extends Hero{
    public Archer(String name){
        super(name, 160, 140);
        skill = new Skill[]{
        skill[0] = new Skill("Arrow Shot",30,40),
        skill[1] = new Skill("Multi Shot", 50, 60),
        skill[2] = new Skill("Peircing Arrow",80 ,100)
        };
    }
     @Override
    public void attack(int skillIndex, Hero target) {
        
        super.attack(skillIndex, target);
    }
    
}
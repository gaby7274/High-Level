


class FloatingBoxController {

    constructor(gltf_scene, collectable_type, scene) {

        this.scene = gltf_scene;

        this.scene.scale.set(15, 15, 15);


        this.collectable_type = collectable_type
        this.rotation = 0.1;


        this.y_flying = 1

    }

    update(absolute) {

        this.scene.rotateY(this.rotation)
        this.scene.translateY(this.y_flying * Math.sin(absolute * 10));


    }




}

export { FloatingBoxController }
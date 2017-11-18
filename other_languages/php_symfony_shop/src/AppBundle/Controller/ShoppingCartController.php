<?php

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use AppBundle\Entity\Commodity;

class ShoppingCartController extends Controller
{
    /**
     * @Route("/shoppingcart", name="shoppingcart")
     */
    public function indexAction(Request $request)
    {
        $repository = $this->getDoctrine()->getRepository(Commodity::class);
        $items = $repository->findAll();
    	$shopping_cart = $_SESSION['shopping_cart'];

        # regenerate the item array, use id as key
        $item_arr = array(); 
        foreach ($items as $item) {
            $item_arr[$item->id] =  $item;
        }

        $total_price = 0.00;
        if(!empty($shopping_cart)) {
            foreach ($shopping_cart as $key => $value) {
                $total_price += $value * $item_arr[$key]->price;
            }
        }

        return $this->render('shop/shoppingcart.html.twig', [
            'shopping_cart' => $shopping_cart,
            'items' => $item_arr,
            'total_price' => $total_price,
        ]);
    }
}
